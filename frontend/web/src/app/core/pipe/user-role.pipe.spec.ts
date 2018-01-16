import {UserRolePipe} from './user-role.pipe';

describe('UserRolePipe', () => {
  it('transform identity', () => {
    const pipe = new UserRolePipe();
    expect(pipe.transform('ROLE_ADMIN')).toEqual('ADMIN')
  });
});
