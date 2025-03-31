import React from 'react';
import { Container, Row, Col, Card, Form, Button } from 'react-bootstrap';
import { LogOut, CircleUser } from 'lucide-react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css'
import SearchCriteriaDropdown from './SearchCriteria.jsx';

const TelaInicial = () => {
  const tasks = [
    { id: 1, title: 'Tarefa 1', category: 'Categoria', date: 'dd/mm/yyyy', status: 'Status' },
    { id: 2, title: 'Tarefa 2', category: 'Categoria', date: 'dd/mm/yyyy', status: 'Status' },
    { id: 3, title: 'Tarefa 3', category: 'Categoria', date: 'dd/mm/yyyy', status: 'Status' },
    { id: 4, title: 'Tarefa 4', category: 'Categoria', date: 'dd/mm/yyyy', status: 'Status' },
    { id: 5, title: 'Tarefa 5', category: 'Categoria', date: 'dd/mm/yyyy', status: 'Status' },
     { id: 6, title: 'Tarefa 6', category: 'Categoria', date: 'dd/mm/yyyy', status: 'Status' },
     { id: 7, title: 'Tarefa 6', category: 'Categoria', date: 'dd/mm/yyyy', status: 'Status' },
     { id: 8, title: 'Tarefa 6', category: 'Categoria', date: 'dd/mm/yyyy', status: 'Status' },
     { id: 9, title: 'Tarefa 6', category: 'Categoria', date: 'dd/mm/yyyy', status: 'Status' },
     { id: 10, title: 'Tarefa 6', category: 'Categoria', date: 'dd/mm/yyyy', status: 'Status' }


  ];

  return (
    <Container 
      fluid 
      className="d-flex flex-column min-vh-100 bg-black p-3"
      style={{ height: '100vh', 
        width: '100vh'
      }}
    >
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
                    <small className="text-white">{task.date}</small>
                    <small className='text-success'>{task.status}</small>
                    <small className="text-white">{task.category}</small>
                  </div>
                  <Card.Title className='text-primary'>{task.title}</Card.Title>
                  <Card.Text className="text-white mb-3">
                    Descrição completa da tarefa bio bio bis bis bis bio bio bis bis...
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