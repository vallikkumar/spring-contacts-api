import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import ContactList from './ContactList';
import ContactEdit from './ContactEdit';

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home}/>
          <Route path='/contacts' exact={true} component={ContactList}/>
          <Route path='/contacts/:id' component={ContactEdit}/>
        </Switch>
      </Router>
    )
  }
}

export default App;