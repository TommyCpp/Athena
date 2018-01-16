const jsonServer = require('json-server');
const server = jsonServer.create();
const router = jsonServer.router('db.json');
const middlewares = jsonServer.defaults();

const readers = {
  11: "12314",
  12: "12234"
};

const admins = {
  16: "1111"
};

const superAdmins = {
  10: "11111"
};

server.use(middlewares);
server.use(jsonServer.bodyParser);
server.use((req, res, next) => {
  res.header("access-control-expose-headers",
    "X-AUTHENTICATION"
  );
  next()
});
server.get('/user', (req, res) => {
  let token = req.header('X-AUTHENTICATION');
  let id = token.substr(token.length - 2);
  res.redirect(`/users/${id}`);
});
server.post('/login', (req, res) => {
  let id = req.body["id"];
  let token = "O8Hp9YH98h98YO89y90T0F87feG80F";
  if (readers.hasOwnProperty(id) && readers[id] === req.body['password']) {
    res.set({
      "X-AUTHENTICATION": token + id
    }).json({
      "id": id,
      "username": "reader" + id,
      "wechat_id": "good_reader_" + id,
      "email": "reader" + id + "@athena.com",
      "phone_number": "15264218" + id,
      "identity": "ROLE_READER"
    });
  } else if (admins.hasOwnProperty(id) && admins[id] === req.body['password']) {
    res.set({
      "X-AUTHENTICATION": token + id
    }).json({
      "id": id,
      "username": "admin" + id,
      "wechat_id": "good_admin_" + id,
      "email": "admin" + id + "@athena.com",
      "phone_number": "15264266" + id,
      "identity": "ROLE_ADMIN"
    })
  } else if (superAdmins.hasOwnProperty(id) && admins[id] === req.body['password']) {
    res.set({"X-AUTHENTICATION": token + id}).json({
      "id": id,
      "username": "superadmin" + id,
      "wechat_id": "good_superadmin_" + id,
      "email": "superadmin" + id + "@athena.com",
      "phone_number": "15264298" + id,
      "identity": "ROLE_SUPERADMIN"
    }).end();
  }
  else {
    //if not found
    res.status(404).end();
  }

});

server.use(router);

server.listen(3000, () => {
  console.log('JSON Server is running')
});



