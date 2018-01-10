export class User {
  public id: number;
  public username: string;
  public identity: string[];

  constructor(id, username, identity?) {
    this.id = id;
    this.username = username;
    if (identity) {
      this.identity = identity;
    }
  }
}
