import React from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import './App.css'
import { ThemeProvider as MuiThemeProvider } from '@material-ui/core/styles';
import createMuiTheme from '@material-ui/core/styles/createMuiTheme'
import themeFile from './util/theme'
import jwtDecode from 'jwt-decode'
// Redux
import {Provider} from 'react-redux'
import store from './redux/store'
// Components
import NavigationBar from "./components/NavigationBar";
import AuthRoute from './util/AuthRoute'
// Pages
import home from './pages/home'
import login from './pages/login'
import signup from './pages/signup'

const theme = createMuiTheme(themeFile);

let authenticated;
const token = localStorage.jwtToken;
if(token) {
    const decodeToken = jwtDecode(token);
    if(decodeToken.exp * 1000 < Date.now()) {
        window.location.href = 'http://localhost:3000/login';
        authenticated = false;
    } else {
        authenticated = true;
    }
}

function App() {
  return (
    <MuiThemeProvider theme={theme}>
        <Provider store={store}>
            <Router>
                <NavigationBar/>
                <div className="container">
                    <Switch>
                        <Route path="/" exact component={home}/>
                        <AuthRoute path="/login" exact component={login} authenticated={authenticated}/>
                        <AuthRoute path="/signup" exact component={signup} authenticated={authenticated}/>
                    </Switch>
                </div>
            </Router>
        </Provider>
    </MuiThemeProvider>
  );
}

export default App;
