import React, { useState } from 'react';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import { ChevronLeft, Plus, Trash2 } from 'lucide-react';

const VisualizarTarefa = () => {
  const [taskData, setTaskData] = useState({
    title: 'Tarefa 1',
    description: '',
    category: '',
    date: '',
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

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setTaskData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Lógica para alterar a tarefa
    console.log(taskData);
  };

  return (
    <Container 
      fluid 
      className="d-flex flex-column w-auto p-5 bg-dark p-3 rounded align-items-center"
      style={{ height: '80vh', width: '100vh' }}
    >
      <Row className="mb-4 align-items-center">
        <Col xs="auto">
          <Button variant="outline-primary" className="border-0">
            <ChevronLeft size={24} />
          </Button>
        </Col>
        <Col>
          <h1 className="mb-0 text-white">Edição de tarefa</h1>
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

        <Row className="mb-3">
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
              <Plus size={20} className="me-2" />
              Adicionar Tarefa
            </Button>
          </Col>
        </Row>

        <Row>
            <Col>
                <Button
                    variant='danger'
                    type='submit'
                    className='mt-4'
                >
                <Trash2 size={20} />
                </Button>
            </Col>
        </Row>
      </Form>
    </Container>
  );
};

export default VisualizarTarefa;