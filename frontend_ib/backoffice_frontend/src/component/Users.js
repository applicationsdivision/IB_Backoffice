import React, { useState, useEffect } from 'react';
import axios from 'axios';
import DataTable from 'react-data-table-component';
import RegisterModal from './RegisterModal';
import './css/Users.css';

const Users = () => {
    const [users, setUsers] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [editUser, setEditUser] = useState(null);

    useEffect(() => {
        fetchUsers();
    }, []);

    const fetchUsers = async () => {
        try {
            const response = await axios.get('http://localhost:8070/api/user/allusers');
            setUsers(response.data);
        } catch (error) {
            console.error('Error fetching users:', error);
        }
    };

    const handleDelete = async (id) => {
        try {
            await axios.delete(`http://localhost:8070/api/user/delete/${id}`);
            fetchUsers();
        } catch (error) {
            console.error('Error deleting user:', error);
        }
    };

    const handleEdit = async (id, updatedUserData) => {
        try {
            await axios.put(`http://localhost:8070/api/user/update/${id}`, updatedUserData, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            fetchUsers();
        } catch (error) {
            console.error('Error updating user:', error.response ? error.response.data : error.message);
        }
    };

    const columns = [
        {
            name: 'Username',
            selector: row => row.username,
            sortable: true,
        },
        {
            name: 'Email',
            selector: row => row.email,
            sortable: true,
        },
        {
            cell: row => <button onClick={() => setEditUser(row)}>Edit</button>,
            ignoreRowClick: true,
            allowOverflow: true,
            button: true,
        },
        {
            cell: row => <button onClick={() => handleDelete(row.id)}>Delete</button>,
            ignoreRowClick: true,
            allowOverflow: true,
            button: true,
        },
    ];

    return (
        <div>
            <h2>All Users</h2>
            <button onClick={() => setShowModal(true)}>Register New User</button>
            <DataTable
                columns={columns}
                data={users}
                pagination
            />
            {showModal && <RegisterModal onClose={() => setShowModal(false)} />}
            {editUser && (
                <div>
                    {/* Your form to update user */}
                    <input
                        type="text"
                        value={editUser.username}
                        onChange={(e) => setEditUser({ ...editUser, username: e.target.value })}
                    />
                    <input
                        type="email"
                        value={editUser.email}
                        onChange={(e) => setEditUser({ ...editUser, email: e.target.value })}
                    />
                    <button onClick={() => handleEdit(editUser.id, { username: editUser.username, email: editUser.email })}>Save</button>
                </div>
            )}
        </div>
    );
};

export default Users;
