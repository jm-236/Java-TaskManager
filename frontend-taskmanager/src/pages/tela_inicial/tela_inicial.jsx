import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Form, Button, Spinner } from 'react-bootstrap';
import { LogOut, CircleUser } from 'lucide-react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css'
import SearchCriteriaDropdown from './SearchCriteria.jsx';

const TelaInicial = () => {
  // 1. Estados para armazenar as tarefas, o status de carregamento e possíveis erros
  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // 2. useEffect para buscar os dados do backend quando o componente for montado
  useEffect(() => {
    const fetchTasks = async () => {
      try {
        // ATENÇÃO: Substitua 'http://seu-backend.com/api/tasks' pela URL real da sua API
        const response = await fetch('http://seu-backend.com/api/tasks');
        
        if (!response.ok) {
          throw new Error(`Erro na requisição: ${response.statusText}`);
        }
        
        const data = await response.json();
        setTasks(data); // Armazena os dados recebidos no estado
      } catch (err) {
        setError(err.message); // Armazena a mensagem de erro no estado
      } finally {
        setLoading(false); // Finaliza o estado de carregamento
      }
    };

    fetchTasks();
  }, []); // O array vazio [] garante que o useEffect execute apenas uma vez

  // 3. Renderização condicional para o estado de carregamento
  if (loading) {
    return (
      <Container className="d-flex justify-content-center align-items-center min-vh-100 bg-black">
        <Spinner animation="border" variant="primary" />
        <span className="text-white ms-3">Carregando tarefas...</span>
      </Container>
    );
  }

  // 4. Renderização condicional para o estado de erro
  if (error) {
    return (
      <Container className="d-flex justify-content-center align-items-center min-vh-100 bg-black">
        <div className="text-center text-danger">
          <h4>Ocorreu um erro ao buscar as tarefas</h4>
          <p>{error}</p>
        </div>
      </Container>
    );
  }

  return (
    <Container 
      fluid 
      className="d-flex flex-column min-vh-100 bg-black p-3"
      style={{ height: '100vh', 
        width: '100vw' // Corrigido de '100vh' para '100vw' para ocupar a largura da tela
      }}
    >
      {/* O restante do seu JSX continua aqui... */}
      <Row className="mb-3">
        <Col>
          <div className="d-flex justify-content-between align-items-center">
            <button className='btn btn-dark bg-transparent border-0 low-opacity'>
                <LogOut size={40} />
            </button>
            <div className="d-flex align-items-center w-50">
                <SearchCriteriaDropdown/>
                <Form.Control 
                  type="text" 
                  placeholder="Pesquisar tarefa" 
                  className="mx-3" 
                />
            </div>
            <Button variant="btn btn-dark bg-transparent border-0 low-opacity">
              <CircleUser size={60}/>
            </Button>
          </div>
        </Col>
      </Row>
      
      <Row className="mb-3">
        <Col>
          <Button variant="success mb-3">
            <i className="bi bi-plus-lg me-2"></i>Adicionar tarefa
          </Button>
        </Col>
      </Row>

      <Row xs={1} md={2} lg={3} className="g-4 flex-grow-1 overflow-auto">
        {tasks.length > 0 ? (
          tasks.map((task) => (
            <Col key={task.id}>
              <Card className="shadow-sm bg-dark">
                <Card.Body>
                  <div className="d-flex justify-content-between align-items mb-2">
                    {/* ATENÇÃO: Garanta que os nomes das propriedades (task.date, etc.) correspondem aos do seu backend */}
                    <small className="text-white">{task.date}</small>
                    <small className='text-success'>{task.status}</small>
                    <small className="text-white">{task.category}</small>
                  </div>
                  <Card.Title className='text-primary'>{task.title}</Card.Title>
                  <Card.Text className="text-white mb-3">
                    {task.description || "Descrição completa da tarefa..."}
                  </Card.Text>
                  <Button variant="outline-primary" className="btn btn-sm w-75">
                    Visualizar tarefa completa
                  </Button>
                </Card.Body>
              </Card>
            </Col>
          ))
        ) : (
          <Col className="d-flex justify-content-center align-items-center w-100">
            <div className="text-center text-muted">
              <i className="bi bi-list-task display-4 mb-3"></i>
              <h4>Nenhuma tarefa encontrada</h4>
              <p>Adicione sua primeira tarefa clicando no botão acima</p>
            </div>
          </Col>
        )}
      </Row>
    </Container>
  );
};

export default TelaInicial;