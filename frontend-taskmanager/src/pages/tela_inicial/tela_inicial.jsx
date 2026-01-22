import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Form, Button, Spinner } from 'react-bootstrap';
import { LogOut, CircleUser, Plus } from 'lucide-react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import SearchCriteriaDropdown from './SearchCriteria.jsx';
import api from '../../services/api';
import Cookies from 'js-cookie';
import { useNavigate } from 'react-router-dom';
 
const TelaInicial = () => {
  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isPopupVisible, setIsPopupVisible] = useState(false);
  const [popupMessage, setPopupMessage] = useState('');
  const fecharPopup = () => {
        setIsPopupVisible(false);
        window.location.href = "/login"
    };

  const logout = () => {

    Cookies.remove("JWT-Cookie")
    // setPopupMessage(`Usuário ${nome} deslogado com sucesso!`);
    setPopupMessage("Usuário deslogado com sucesso!")
    setIsPopupVisible(true);

  }

  const navigate = useNavigate();

  const detalhes_tarefa = (task) => {

    navigate('/tarefa', {state: task})
  }

  useEffect(() => {
    const fetchTasks = async () => {
      try {
        setLoading(true);
        const response = await api.get('/user/tasks', { withCredentials: true });
        console.log(response.data)
        setTasks(response.data);
        
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchTasks();
  }, []);

  if (loading) {
    return (
      <Container className="d-flex justify-content-center align-items-center min-vh-100 bg-black">
        <Spinner animation="border" variant="primary" />
        <span className="text-white ms-3">Carregando tarefas...</span>
      </Container>
    );
  }

  if (error) {
    return (
      <Container className="d-flex justify-content-center align-items-center min-vh-100 bg-black">
        <div className="text-center text-danger p-3">
          <h4>Ocorreu um erro ao buscar as tarefas</h4>
          <p>{error}</p>
          <Button variant="outline-danger" onClick={() => window.location.reload()}>Tentar novamente</Button>
        </div>
      </Container>
    );
  }

  return (
    <> 
    <Container 
      fluid 
      className="bg-black text-white min-vh-100 p-0" // Removi o padding lateral do container pai
    >
    <div className="p-3 p-md-4"> {/* Wrapper interno para dar respiro sem encolher o container */}
    
    {/* HEADER AJUSTADO */}
    <Row className="align-items-center mb-4 gx-0"> {/* gx-0 remove espaços laterais da row */}
      <Col xs={2} md={1} className="text-start">
        <button className='btn btn-dark bg-transparent border-0 p-0 low-opacity' onClick={logout}>
          <LogOut size={32} />
        </button>
      </Col>
      
      <Col xs={8} md={10}>
        <div className="d-flex align-items-center bg-dark rounded-pill px-3 py-2" style={{ border: '1px solid #333' }}>
          <SearchCriteriaDropdown />
          <Form.Control 
            type="text" 
            placeholder="Pesquisar tarefas..." 
            className="bg-transparent border-0 text-white shadow-none focus-none w-100" 
          />
        </div>
      </Col>

      <Col xs={2} md={1} className="text-end">
        <Button variant="dark bg-transparent border-0 p-0 low-opacity">
          <CircleUser size={48} />
        </Button>
      </Col>
    </Row>
      
      {/* BOTÃO ADICIONAR */}
      <Row className="mb-4 justify-content-center">
        <div>
          <Button variant="success" className="align-items-center rounded-pill px-4 py-2 fw-bold" onClick={() => window.location.href = '/criar_tarefa'}>
            <Plus size={20} className="me-2 justify-content-center" />
            Adicionar tarefa
          </Button>
        </div>
      </Row>

      {/* GRID DE TAREFAS */}
      <Row xs={1} sm={2} lg={3} xl={4} className="g-3 justify-content-center">
        {tasks.length > 0 ? (
          tasks.map((task) => (
            <Col key={task.uuid}>
              <Card className="h-100 border-secondary bg-dark text-white shadow-sm hover-card">
                <Card.Body className="d-flex flex-column">
                  <div className="d-flex justify-content-between align-items-start mb-2">
                    <small className="text-muted">{task.createdDate}</small>
                    <span className={`badge ${task.status === 'Concluído' ? 'bg-success' : 'bg-primary'}`}>
                      {task.status}
                    </span>
                  </div>
                  
                  <Card.Title className="text-info fs-5 mb-2">{task.title}</Card.Title>
                  
                  <Card.Text className="text-light opacity-75 mb-4 flex-grow-1">
                    {task.description ? (task.description.substring(0, 100) + '...') : "Sem descrição disponível."}
                  </Card.Text>
                  
                  <div className="mt-auto pt-3 border-top border-secondary">
                    <div className="d-flex justify-content-between align-items-center">
                      <small className="text-info">#{task.category}</small>
                      <Button variant="outline-primary" size="sm" onClick={() => detalhes_tarefa(task)}>
                        Ver Detalhes
                      </Button>
                    </div>
                  </div>
                </Card.Body>
              </Card>
            </Col>
          ))
        ) : (
          <Col xs={12} className="text-center py-5">
            <div className="text-muted">
              <h4>Nenhuma tarefa encontrada</h4>
              <p>Sua lista está limpa por enquanto!</p>
            </div>
          </Col>
        )}
      </Row>
      </div>  
  </Container>
  {/* Pop-up condicional */}
        {isPopupVisible && (
            <div className="popup-overlay">
                <div className="popup-content">
                    <h2>Aviso!</h2>
                    <p>{popupMessage}</p>
                    <button className='btn btn-sm btn-primary color-white' onClick={fecharPopup}>Fechar</button>
                </div>
            </div>
        )}
  </>
)
};

export default TelaInicial;