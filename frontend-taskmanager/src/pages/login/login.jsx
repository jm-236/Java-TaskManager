import React from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import './index.css'

function Login() {
    return (
        <>
            <div className='min-h-screen flex bg-gray-100 p-4'>
                <h1 className='text-white display-1 pb-5 item align-self-center'>Task Manager</h1>
                <div className='bg-dark rounded container d-flex flex-column py-5 login-form item align-self-center'>
                    <h1 className='text-white py-2'>Login</h1>
                    <input type="text" placeholder='Email' className='text-white my-2 item align-self-center' />
                    <input type="password" placeholder='Senha' name="" id="" className='text-white my-2 item align-self-center' />
                    <a href="" className=''>Esqueci minha senha</a>
                    <button className='btn btn-sm btn-primary color-white my-4 item align-self-center px-3 py-2'>Realizar login</button>
                </div>
            </div>
        </>
    )
}

export default Login