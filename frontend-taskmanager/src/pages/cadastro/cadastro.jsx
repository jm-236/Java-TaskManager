import React from 'react'
import { ArrowLeft } from 'lucide-react';
import 'bootstrap/dist/css/bootstrap.min.css'
import './index.css'

function Cadastro() {
    return (
        <>
            <div className='min-h-screen flex items-center justify-center bg-gray-100 p-4'>
                <h1 className='text-white display-1 pb-5'>Task Manager</h1>
                <div className='bg-dark rounded caixa container d-flex flex-column p-2 login-form clearfix'>
                    <button className="btn btn-dark py-2 item align-self-start arrow rounded-circle bg-transparent">
                        <ArrowLeft size={20} />
                    </button>
                    <h1 className='text-white py-2'>Cadastro de usuário</h1>
                    <input type="text" placeholder='Nome' className='text-white my-2 item align-self-center' />
                    <input type="text" placeholder='Email' className='text-white my-2 item align-self-center' />
                    <input type="password" placeholder='Senha' name="" id="" className='text-white my-2 item align-self-center' />
                    <input type="password" placeholder='Confirmar senha' name="" id="" className='text-white my-2 item align-self-center' />
                    <button className='btn btn-sm btn-primary color-white my-4 item align-self-center px-3 py-2'>Realizar cadastro</button>
                </div>
            </div>
        </>
    )
}
export default Cadastro