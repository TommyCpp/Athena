db.auth('admin', 'admin');

db = db.getSiblingDB('athena');

db.createUser({
    user: 'admin',
    pwd: 'adminpassword',
    roles: [
        {
            role: 'readWrite',
            db: 'athena'
        }
    ]
});