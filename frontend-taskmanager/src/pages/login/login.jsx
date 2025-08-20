import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.min.css'
import './index.css'
import api from '../../services/api';

function TelaLogin() {
    const [email, setEmail] = useState('')
    const [senha, setSenha] = useState('')
    const [erro, setErro] = useState(null)

    const handleLogin = async () => {
        try {
            
            const usuario = { 'email': email, 'password': senha }

            api.post('auth/login', usuario, {withCredentials: true})
            .then(
                resposta => {
                    console.log("Status recebido: " + resposta.status)
                    console.log("Dados recebidos: " + resposta.data)
                }
            ) 
            .catch(
                error => {
                    if (error.response) {

                        if (error.response.status === 400) {
                            setErro(error.response.data.erro)
                        }
                    } else if (error.request) {
                        // A requisição foi feita, mas não houve resposta
                        console.log(error.request);
                        setErro("Erro durante realização do Cadastro.")
                    } else {
                        // Algum erro ocorreu durante a configuração da requisição
                        console.log('Error', error.message);
                        setErro("Erro desconhecido.")
                    }
                }
            )
            

        } catch (err) {
            setErro(err.message)
        }
    }

    return (
        <div className='min-h-screen flex bg-gray-100 p-4'>
            <h1 className='text-white display-1 pb-5 item align-self-center'>Task Manager</h1>
            <div className='bg-dark rounded container d-flex flex-column py-5 login-form item align-self-center'>
                <h1 className='text-white py-2'>Login</h1>
                
                <input
                    type="text"
                    placeholder='Email'
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className='text-white my-2 item align-self-center'
                />

                <input
                    type="password"
                    placeholder='Senha'
                    value={senha}
                    onChange={(e) => setSenha(e.target.value)}
                    className='text-white my-2 item align-self-center'
                />

                <a href="" className='text-white text-sm text-decoration-underline'>Esqueci minha senha</a>
                <Link className='text-white text-sm text-decoration-underline' to='/register'>Realizar cadastro</Link>

                {erro && <div className='text-danger mt-2'>{erro}</div>}

                <button
                    className='btn btn-sm btn-primary color-white my-4 item align-self-center px-3 py-2'
                    onClick={handleLogin}
                >
                    Realizar login
                </button>
            </div>
        </div>
    )
}

export default TelaLogin
