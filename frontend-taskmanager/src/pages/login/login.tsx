import React from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import './index.css'

function Login() {
    return (
        <>
        <div className='body'>
            <div className='bg-dark rounded caixa container d-flex flex-column p-5 login-form'>
                <h1 className='text-white py-3'>Task Manager</h1>
                <input type="text" placeholder='Email' className='text-white my-1'/>
                <input type="password" placeholder='Senha' name="" id="" className='text-white my-3'/>
                <a href="">Esqueci minha senha</a>
                <button className='btn btn-primary color-white my-3'>Login</button>
            </div>
        </div>
        </>
    )
}

export default Login