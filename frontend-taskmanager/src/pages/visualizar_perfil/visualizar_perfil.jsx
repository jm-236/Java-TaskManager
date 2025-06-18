import React, { useState } from 'react';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import { ChevronLeft, Check, Trash2 } from 'lucide-react';
import 'bootstrap/dist/css/bootstrap.min.css';

const VisualizarPerfil = () => {
  const [userData, setUserData] = useState({
    name: 'Nome do usuário',
    email: 'email@exemplo.com'
  });

  const [isEditingName, setIsEditingName] = useState(false);
  const [isEditingEmail, setIsEditingEmail] = useState(false);

  const handleEditName = () => {
    setIsEditingName(!isEditingName);
  };

  const handleEditEmail = () => {
    setIsEditingEmail(!isEditingEmail);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUserData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSaveChanges = () => {
    // Lógica para salvar alterações no servidor
    console.log('Salvando alterações:', userData);
    setIsEditingName(false);
    setIsEditingEmail(false);
  };

  const handleDeleteAccount = () => {
    // Lógica para excluir conta
    if (window.confirm('Tem certeza que deseja excluir sua conta? Esta ação não pode ser desfeita.')) {
      console.log('Conta excluída');
    }
  };

  return (
    <Container fluid className="d-flex justify-content-center align-items-center bg-dark min-vh-100">
      <div style={{ 
        backgroundColor: '#5a5a5a', 
        borderRadius: '16px', 
        maxWidth: '400px',
        width: '100%',
        padding: '20px'
      }}>
        <div className="d-flex align-items-center mb-4">
          <Button 
            variant="link" 
            className="text-white p-0 me-3"
            style={{ backgroundColor: 'transparent', border: 'none' }}
          >
            <ChevronLeft size={24} />
          </Button>
          <h2 className="m-0 text-white">Meu perfil</h2>
        </div>

        <Form>
          <div className="mb-3">
            <div className="d-flex align-items-center">
              <Form.Control
                type="text"
                name="name"
                value={userData.name}
                onChange={handleInputChange}
                disabled={!isEditingName}
                className="mb-2"
                style={{ borderRadius: '50px' }}
              />
              <Button 
                variant="link" 
                onClick={handleEditName}
                className="ms-2 text-white"
                style={{ 
                  backgroundColor: isEditingName ? '#5FB14D' : 'transparent', 
                  border: 'none',
                  borderRadius: '50%',
                  width: '32px',
                  height: '32px',
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center',
                  padding: '0'
                }}
              >
                <Check size={20} />
              </Button>
            </div>
            <small className="text-white">Alterar nome</small>
          </div>

          <div className="mb-4">
            <div className="d-flex align-items-center">
              <Form.Control
                type="email"
                name="email"
                value={userData.email}
                onChange={handleInputChange}
                disabled={!isEditingEmail}
                className="mb-2"
                style={{ borderRadius: '50px' }}
              />
              <Button 
                variant="link" 
                onClick={handleEditEmail}
                className="ms-2 text-white"
                style={{ 
                  backgroundColor: isEditingEmail ? '#5FB14D' : 'transparent', 
                  border: 'none',
                  borderRadius: '50%',
                  width: '32px',
                  height: '32px',
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center',
                  padding: '0'
                }}
              >
                <Check size={20} />
              </Button>
            </div>
            <small className="text-white">Alterar email</small>
          </div>

          <div className="d-flex flex-column align-items-center">
            <Button 
              variant="primary" 
              onClick={handleSaveChanges}
              className="mb-3 w-100"
              style={{ 
                borderRadius: '50px', 
                backgroundColor: '#2D6DCC',
                border: 'none'
              }}
            >
              Salvar alterações
            </Button>
            
            <Button 
              variant="warning" 
              className="mb-4 w-100"
              style={{ 
                borderRadius: '50px', 
                backgroundColor: '#F9BE4B',
                border: 'none'
              }}
            >
              Limpar tarefas
            </Button>

            <Button 
              variant="danger" 
              onClick={handleDeleteAccount}
              className="rounded-circle"
              style={{ 
                width: '48px',
                height: '48px',
                backgroundColor: '#FF3D3D',
                border: 'none',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center'
              }}
            >
              <Trash2 size={24} />
            </Button>
          </div>
        </Form>
      </div>
    </Container>
  );
};

export default VisualizarPerfil;