import { Link, useNavigate } from 'react-router-dom';
import ButtonIcon from '../../../../components/ButtonIcon';
import './styles.css';
import { useForm } from 'react-hook-form';
import {
  getTokenData,
} from '../../../../util/auth';
import { useContext, useState } from 'react';
import { AuthContext } from '../../../../AuthContext';
import { requestBackendLogin } from '../../../../util/request';
import { getAuthData, saveAuthData } from '../../../../util/storage';

const Login = () => {
  const { setAuthContextData } = useContext(AuthContext);

  type CredentialDTO = {
    username: string;
    password: string;
  };

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<CredentialDTO>();

  const [hasError, setHasError] = useState(false);

  const navigate = useNavigate();

  const onSubmit = (formData: CredentialDTO) => {
    requestBackendLogin(formData)
      .then((response) => {
        saveAuthData(response.data);
        const token = getAuthData().access_token;
        console.log('TOKEN GERADO' + token);
        setHasError(false);
        console.log('seucesso', response);
        setAuthContextData({
          autheticated: true,
          tokenData: getTokenData(),
        });
        navigate('/admin'); // Navega para a rota '/admin'
      })
      .catch((error) => {
        setHasError(true);
        console.log('ERRO', error);
      });
  };

  return (
    <div className="base-card login-card">
      <h1>LOGIN</h1>
      {hasError && (
        <div className="alert alert-danger">Erro ao tentar fazer login!</div>
      )}
      <form onSubmit={handleSubmit(onSubmit)}>
        <div className="mb-4">
          <input
            {...register('username', {
              required: 'Campo obrigatório',
              pattern: {
                value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                message: 'Email inválido',
              },
            })}
            type="text"
            className={`form-control base-input ${
              errors.username ? 'is-invalid' : ''
            }`}
            placeholder="Email"
            name="username"
          />
          <div className="invalid-feedback d-block">
            {errors.username?.message}
          </div>
        </div>
        <div className="mb-2">
          <input
            {...register('password', {
              required: 'Campo obrigatório',
            })}
            type="password"
            className={`form-control base-input ${
              errors.password ? 'is-invalid' : ''
            }`}
            placeholder="Password"
            name="password"
          />
          <div className="invalid-feedback d-block">
            {errors.password?.message}
          </div>
        </div>
        <Link to="/admin/auth/recover" className="login-link-recover">
          Esqueci a senha
        </Link>
        <div className="login-submit">
          <ButtonIcon text="Fazer login" />
        </div>
        <div className="signup-container">
          <span className="not-registered">Não tem Cadastro?</span>
          <Link to="/admin/auth/register" className="login-link-register">
            CADASTRAR
          </Link>
        </div>
      </form>
    </div>
  );
};

export default Login;
