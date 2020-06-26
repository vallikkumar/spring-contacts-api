import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class ContactList extends Component {

  constructor(props) {
    super(props);
    this.state = {contacts: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch('api/contacts')
      .then(response => response.json())
      .then(data => this.setState({contacts: data, isLoading: false}));
  }

  async remove(id) {
    await fetch(`/api/contact/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedContacts = [...this.state.contacts].filter(i => i.id !== id);
      this.setState({contacts: updatedContacts});
    });
  }

  render() {
    const {contacts, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const contactList = contacts.map(contact => {
      const address = `${contact.address || ''} ${contact.city || ''} ${contact.stateOrProvince || ''}`;
      return <tr key={contact.id}>
        <td style={{whiteSpace: 'nowrap'}}>{contact.name}</td>
        <td>{address}</td>
        <td>{contact.phones.map(phone => {
          return <div key={phone.id}>{phone.number}</div>
        })}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/contacts/" + contact.id}>Edit</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(contact.id)}>Delete</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/contacts/new">Add Contact</Button>
          </div>
          <h3>My Contacts</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="20%">Name</th>
              <th width="30%">Address</th>
              <th width="20%">Phone</th>
              <th width="10%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {contactList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default ContactList;