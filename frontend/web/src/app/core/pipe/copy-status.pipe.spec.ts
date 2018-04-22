import { CopyStatusPipe } from './copy-status.pipe';
import {CopyStatus} from '../model/copy';

describe('CopyStatusPipe', () => {
  it('create an instance', () => {
    const pipe = new CopyStatusPipe();
    expect(pipe.transform(1)).toBeTruthy(CopyStatus.AVAILABLE);
  });
});
