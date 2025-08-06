import React from 'react';
import './index.css'
import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext'; // Importa nosso hook

const RotaProtegida = ({ children }) => {
    const { isAuthenticated, isLoading } = useAuth();

    // Enquanto estiver verificando a autenticação, mostre um loading.
    // Isso evita um "flash" da tela de login antes da verificação terminar.
    if (isLoading) {
        return <div>Carregando...</div>; // Ou um componente de Spinner/Loading
    }

    // Se não estiver autenticado, redireciona para a tela de login
    if (!isAuthenticated) {
        return <Navigate to="/login" replace />;
    }

    // Se estiver autenticado, renderiza a página solicitada
    return children;
};

export default RotaProtegida;