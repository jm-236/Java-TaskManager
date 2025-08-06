import React, { useState } from 'react'
import { ArrowLeft } from 'lucide-react';
import { Link, Navigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css'
import './index.css'
import api from '../../services/api';

function Cadastro() {

    const [email, setEmail] = useState('')
    const [name, setName] = useState('')
    const [password, setPassword] = useState('')
    const[senhaConfirmacao, setSenhaConfirmacao] = useState('')
    const [erro, setErro] = useState(null)

    const handleCadastro = async () => {
        try {
            if (name == ''){
                setErro("Digite seu nome para poder se cadastrar.")
                return
            }

            if (email == ''){
                setErro("Digite seu email para poder se cadastrar.")
                return
            }

            if (password != senhaConfirmacao) {
                console.log("Senhas diferentes!")
                setErro("As senhas digitadas devem ser iguais.")
                return
            }

            const novoUser = {
                'name' : name,
                'password': password,
                'email': email
            }

            await api.post('/auth/register', novoUser, { withCredentials: true})
            .then(
                resposta => {
                    const nome = resposta.data.name
                    console.log('Usuário novo cadastrado:', nome)
                    // evoluir pra exibir um pop-up na tela depois

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

            // window.location.href = '/inicio' // ou outra rota

        } catch (err) {
            console.log(err)
            setErro("Erro desconhecido.")
        }
    }


    return (
        <>
            <div className='min-h-screen flex items-center justify-center bg-gray-100 p-4'>
                <h1 className='text-white display-1 mb-3'>Task Manager</h1>
                <div className='bg-dark rounded caixa container d-flex flex-column p-2 login-form clearfix'>
                    
                    <button className="btn btn-dark py-2 item align-self-start arrow rounded-circle bg-transparent">
                        <Link to='/login'>
                        <ArrowLeft size={20} />
                        </Link>
                    </button>
                    
                    <h1 className='text-white mt-0 mb-3'>Cadastro de usuário</h1>
                    
                    <input 
                        type="text" 
                        placeholder='Nome' 
                        onChange={(e) => setName(e.target.value)}
                        className='text-white my-2 item align-self-center'
                    />
                    
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
                        onChange={(e) => setPassword(e.target.value)} 
                        className='text-white my-2 item align-self-center' />
                    
                    <input 
                        type="password" 
                        placeholder='Confirmar senha' 
                        onChange={(e) => setSenhaConfirmacao(e.target.value)}
                        className='text-white my-2 item align-self-center' 
                    />

                    {erro && <div className='text-danger mt-1'>{erro}</div>}

                    <button 
                        className='btn btn-sm btn-primary color-white my-4 item align-self-center px-3 py-2'
                        onClick={handleCadastro}
                    >Realizar cadastro</button>
                </div>
            </div>
        </>
    )
}
export default Cadastro