import { useState } from 'react';
import { ArrowLeft } from 'lucide-react';
import { useNavigate } from 'react-router-dom'; // Para navegar
import api from '../../services/api';
import 'bootstrap/dist/css/bootstrap.min.css';

function MudarSenha() {
    const [email, setEmail] = useState('');
    const [status, setStatus] = useState({ type: '', message: '' }); // Feedback
    const navigate = useNavigate();

    const handleUserEmail = async () => {
        try {
            setStatus({ type: 'info', message: 'Enviando...' });
            await api.post('/user/password-reset', { email }, { withCredentials: true });
            setStatus({ type: 'success', message: 'Se o email existir, um link foi enviado!' });
        } catch (error) {
            setStatus({ type: 'error', message: 'Erro ao processar solicitação.' });
        }
    };

    return (
        <div className='min-h-screen flex items-center justify-center bg-gray-100 p-4'>
            <div className='bg-dark rounded container d-flex flex-column p-4 login-form' style={{maxWidth: '400px'}}>
                <button 
                    className="btn btn-dark align-self-start rounded-circle mb-3"
                    onClick={() => navigate('/login')}
                >
                    <ArrowLeft size={20} />
                </button>
                
                <h2 className='text-white mb-3'>Redefinição de senha</h2>
                <p className='text-white-50 mb-4'>Insira o email da sua conta para receber o link de recuperação:</p>
                
                <input 
                    type="email" 
                    placeholder='Email' 
                    onChange={(e) => setEmail(e.target.value)} 
                    className='form-control mb-3 bg-transparent text-white' 
                />

                {status.message && (
                    <div className={`alert ${status.type === 'success' ? 'alert-success' : 'alert-danger'} py-2`}>
                        {status.message}
                    </div>
                )}

                <button 
                    className='btn btn-primary w-100 py-2 mt-2' 
                    onClick={handleUserEmail}
                    disabled={!email}
                >
                    Enviar link
                </button>
            </div>
        </div>
    );
}
export default MudarSenha;