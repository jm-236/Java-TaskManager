import React from 'react'
import { ArrowLeft } from 'lucide-react';
import 'bootstrap/dist/css/bootstrap.min.css'
import './index.css'

function MudarSenha() {
    return (
        <>
            <div className='min-h-screen flex items-center justify-center bg-gray-100 p-4'>
                <h1 className='text-white display-1 mb-3'>Task Manager</h1>
                <div className='bg-dark rounded caixa container d-flex flex-column p-2 login-form clearfix'>
                    <button className="btn btn-dark py-2 item align-self-start arrow rounded-circle bg-transparent">
                        <ArrowLeft size={20} />
                    </button>
                    <h1 className='text-white mt-0 mb-2'>Redefinição de senha</h1>
                    <p className='text-white mb-2 mt-4'>Insira o email da sua conta para <br></br> redefinir sua senha: </p>
                    <input type="text" placeholder='Email' className='text-white my-0 item align-self-center' />
                    <button className='btn btn-sm btn-primary color-white my-4 item align-self-center px-3 py-2'>Enviar link</button>
                </div>
            </div>
        </>
    )
}
export default MudarSenha