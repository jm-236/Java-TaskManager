import React, { useState } from 'react';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import { ChevronLeft, Plus, Trash2 } from 'lucide-react';
import { useLocation } from 'react-router-dom';
import api from '../../services/api';

const VisualizarTarefa = () => {

  const location = useLocation();
  const task = location.state; // Aqui estão os seus valores

  const [isPopupVisible, setIsPopupVisible] = useState(false);
  const [popupMessage, setPopupMessage] = useState('');

  const fecharPopup = () => {
    setIsPopupVisible(false);
    if (popupMessage.includes("deleted with sucess")){
      window.location.href = "/inicio"
    }
  }

  const [taskData, setTaskData] = useState({
    title: task.title,
    description: task.description,
    category: task.category,
    date: task.date,
    status: task.status
  });

  const retornar_tela_inicial = () => {
    window.location.href = "/inicio"
  }

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

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setTaskData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const salvar_tarefa_modificada = async (e) => {

    // sem esse comando, a página recarrega sozinha e a requisição sequer é realizada
    e.preventDefault();

    // Lógica para alterar a tarefa
    const response = await api.put(`user/tasks/${task.uuid}`, taskData)
  
    if (response.status == 200){
      setPopupMessage(`Tarefa "${taskData.title}" alterada com sucesso!`);
      setIsPopupVisible(true);
    }
    else {
      setPopupMessage(`Erro ao salvar mudanças na tarefa "${taskData.title}".`);
      setIsPopupVisible(true);
      taskData["title"] = task.title
      taskData["description"] = task.description 
      taskData["category"] = task.category
      taskData["status"] = task.status
    }

  }

  const excluir_tarefa = async (e) => {

    e.preventDefault();

    const response = await api.delete(`/user/tasks/${task.uuid}`)

    if (response.status == 200){
      setPopupMessage(response.data);
      setIsPopupVisible(true);
    }
    else {
      setPopupMessage(`Erro ao excluir a tarefa "${taskData.title}".`);
      setIsPopupVisible(true);
    }
  }

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
            <ChevronLeft size={24} />
          </Button>
        </Col>
        <Col>
          <h1 className="mb-0 text-white">Edição de tarefa</h1>
        </Col>
      </Row>

      <Form onSubmit={salvar_tarefa_modificada}>
        <Row className="mb-3">
          <Col>
            <Form.Group>
              <h1>oi</h1>
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
              <Plus size={20} className="me-2" onClick={salvar_tarefa_modificada} />
              Salvar Tarefa Modificada
            </Button>
          </Col>
        </Row>

        <Row>
            <Col>
                <Button
                    variant='danger'
                    type='submit'
                    className='mt-4'
                    onClick={excluir_tarefa}
                >
                <Trash2 size={20} />
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

export default VisualizarTarefa;