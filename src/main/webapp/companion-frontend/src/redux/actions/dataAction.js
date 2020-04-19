import {
    LOADING_DATA, SET_USER
} from '../types';
import axios from 'axios';

export const getUserData = (username) => (dispatch) => {
    dispatch({ type: LOADING_DATA });
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