export class User {
  public id: number;
  public username: string;
  public identity: string[];
  public wechatId: string;
  public email: string;
  public phoneNumber: string;

  constructor(id, username, identity?) {
    this.id = id;
    this.username = username;
    if (identity) {
      this.identity = identity;
    }
  }
}
