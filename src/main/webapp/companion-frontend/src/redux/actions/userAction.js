import {
    SET_USER,
    SET_ERRORS,
    CLEAR_ERRORS,
    LOADING_UI,
    SET_UNAUTHENTICATED,
} from '../types';
import axios from 'axios';

export const loginUser = (userData, history) => (dispatch) => {
    dispatch({ type: LOADING_UI });
    axios.post('http://localhost:8080/authenticate', userData)
        .then(res => {
            const jwtToken = `Bearer ${res.data.jwt}`;
            localStorage.setItem('jwtToken', jwtToken);
            axios.defaults.headers.common['Authorization'] = jwtToken;
            dispatch(getUserData(userData.username));
            dispatch({ type: CLEAR_ERRORS});
            history.push('/');
        })
        .catch(err => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data
            })
        });
};

export const getUserData = (username) => (dispatch) => {
    const url = `http://localhost:8080/user/${username}`;
    axios.get(url)
        .then(res => {
            dispatch({
                type: SET_USER,
                payload: res.data
            })
        })
        .catch(err => console.log(err))
};