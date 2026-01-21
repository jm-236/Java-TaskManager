import React, { useState } from 'react';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import { ChevronLeft, Plus } from 'lucide-react';
import api from '../../services/api';
import Cookies from 'js-cookie';
import { useAuth } from '../../context/AuthContext';

const NovaTarefa = () => {
  const { userEmail, isAuthenticated } = useAuth();

  const [isPopupVisible, setIsPopupVisible] = useState(false);
  const [popupMessage, setPopupMessage] = useState('');

  const fecharPopup = () => {
    setIsPopupVisible(false);
    // Redireciona para a página inicial após fechar o pop-up de sucesso
    if (popupMessage.includes('sucesso')) {
        window.location.href = '/inicio';
    }
  }

  const [taskData, setTaskData] = useState({
    title: '',
    description: '',
    category: '',
    status: ''
  });

  const categories = [
    'Trabalho', 
    'Pessoal', 
    'Estudos', 
    'Saúde', 
    'Outros'
  ];

  const status = [
    'Não Iniciado', 
    'Em Andamento', 
    'Concluído', 
    'Pendente'
  ];

  const retornar_tela_inicial = () => {
    window.location.href = "/inicio"
  }

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setTaskData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    const hoje = new Date();

    e.preventDefault();
    taskData['email'] = userEmail;
    taskData['createdDate'] = hoje; 
    console.log(taskData);
    const response = await api.post('/user/tasks', taskData)
    
    if (response.status == 200){
          setPopupMessage(`Tarefa "${taskData.title}" salva com sucesso!`);
          setIsPopupVisible(true);
    } else {
          setPopupMessage(`Erro ao salvar a tarefa "${taskData.title}".`);
          setIsPopupVisible(true);
          taskData["title"] = ""
          taskData["description"] = ""
          taskData["category"] = ""
          taskData["status"] = ""
    }
  };

  return (
    <>
    <Container 
      fluid 
      className="d-flex flex-column w-auto p-5 bg-dark p-3 rounded align-items-center"
      style={{ height: '80vh', width: '100vh' }}
    >
      <Row className="mb-4 align-items-center">
        <Col xs="auto">
          <Button variant="outline-primary" className="border-0" onClick={retornar_tela_inicial}>
            <ChevronLeft size={24}/>
          </Button>
        </Col>
        <Col>
          <h1 className="mb-0 text-white">Adicionar Tarefa</h1>
        </Col>
      </Row>

      <Form onSubmit={handleSubmit}>
        <Row className="mb-3">
          <Col>
            <Form.Group>
              <Form.Label>Título da Tarefa</Form.Label>
              <Form.Control 
                type="text" 
                name="title"
                value={taskData.title}
                onChange={handleInputChange}
                placeholder="Digite o título da tarefa"
                required
              />
            </Form.Group>
          </Col>
        </Row>

        <Row className="mb-3">
          <Col>
            <Form.Group>
              <Form.Label>Descrição</Form.Label>
              <Form.Control 
                as="textarea" 
                name="description"
                value={taskData.description}
                onChange={handleInputChange}
                placeholder="Descreva os detalhes da tarefa"
                rows={3}
              />
            </Form.Group>
          </Col>
        </Row>

        <Row className="mb-3">
          <Col>
            <Form.Group>
              <Form.Label>Categoria</Form.Label>
              <Form.Select
                name="category"
                value={taskData.category}
                onChange={handleInputChange}
                required
              >
                <option value="">Selecione uma categoria</option>
                {categories.map(category => (
                  <option key={category} value={category}>
                    {category}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>
          </Col>
        </Row>

        {/* <Row className="mb-3">
          <Col>
            <Form.Group>
              <Form.Label>Data</Form.Label>
              <Form.Control 
                type="date" 
                name="date"
                value={taskData.date}
                onChange={handleInputChange}
                required
              />
            </Form.Group>
          </Col>
        </Row> */}

        <Row className="mb-3">
          <Col>
            <Form.Group>
              <Form.Label>Status</Form.Label>
              <Form.Select
                name="status"
                value={taskData.status}
                onChange={handleInputChange}
                required
              >
                <option value="">Selecione o status</option>
                {status.map(stat => (
                  <option key={stat} value={stat}>
                    {stat}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>
          </Col>
        </Row>

        <Row>
          <Col>
            <Button 
              variant="primary" 
              type="submit" 
              className="w-100 d-flex align-items-center justify-content-center"
            >
              <Plus size={20} className="me-2" />
              Adicionar Tarefa
            </Button>
          </Col>
        </Row>
      </Form>
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
  );
};

export default NovaTarefa;