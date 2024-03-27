import { Person } from './person';

describe('Person', () => {
  it('should create an instance', () => {
    // @ts-ignore
    expect(new Person()).toBeTruthy();
  });
});
