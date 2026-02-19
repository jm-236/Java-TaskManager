import { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { ArrowLeft } from 'lucide-react';
import api from '../../services/api';

function NovaSenha() {
    const { token } = useParams(); // Pega o token da URL
    const navigate = useNavigate();
    
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [status, setStatus] = useState({ type: '', message: '' });

    const handleResetPassword = async () => {
        
        if (password !== confirmPassword) {
            setStatus({ type: 'error', message: 'As senhas não coincidem!' });
            return;
        }

        try {
            await api.post(`/user/reset/${token}`, { newPassword: password });
            
            setStatus({ type: 'success', message: 'Senha alterada com sucesso! Redirecionando...' });
            
            setTimeout(() => navigate('/login'), 3000); // Redireciona após 3 segundos
        } catch (error) {
            setStatus({ type: 'error', message: 'Token inválido ou expirado.' });
        }
    };

    return (
        <div className='min-h-screen flex items-center justify-center bg-gray-100 p-4'>
            <div className='bg-dark rounded container d-flex flex-column p-4 login-form' style={{maxWidth: '400px'}}>
                <button className="btn btn-dark align-self-start rounded-circle mb-3" onClick={() => navigate('/login')}>
                    <ArrowLeft size={20} />
                </button>

                <h2 className='text-white mb-2'>Nova Senha</h2>
                <p className='text-white-50 mb-4'>Crie uma nova senha segura para sua conta.</p>

                <input 
                    type="password" 
                    placeholder='Nova Senha' 
                    className='form-control mb-3 bg-transparent text-white'
                    onChange={(e) => setPassword(e.target.value)}
                />
                <input 
                    type="password" 
                    placeholder='Confirmar Senha' 
                    className='form-control mb-3 bg-transparent text-white'
                    onChange={(e) => setConfirmPassword(e.target.value)}
                />

                {status.message && (
                    <div className={`alert ${status.type === 'success' ? 'alert-success' : 'alert-danger'} py-2`}>
                        {status.message}
                    </div>
                )}

                <button 
                    className='btn btn-primary w-100 py-2 mt-2'
                    onClick={handleResetPassword}
                >
                    Redefinir senha
                </button>
            </div>
        </div>
    );
}
export default NovaSenha;