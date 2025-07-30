import React, { useState } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import './index.css'

function TelaLogin() {
    const [email, setEmail] = useState('')
    const [senha, setSenha] = useState('')
    const [erro, setErro] = useState(null)

    const handleLogin = async () => {
        try {
            const resposta = await fetch('http://localhost:8080/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, senha }),
            })

            if (!resposta.ok) {
                throw new Error('Login inválido')
            }

            const dados = await resposta.json()
            console.log('Token recebido:', dados.token)

            // Armazena o token, redireciona, etc.
            localStorage.setItem('token', dados.token)
            window.location.href = '/home' // ou outra rota

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
