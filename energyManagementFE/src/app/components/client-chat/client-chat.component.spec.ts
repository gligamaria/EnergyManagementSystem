import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientChatComponent } from './client-chat.component';

describe('ClientChatComponent', () => {
  let component: ClientChatComponent;
  let fixture: ComponentFixture<ClientChatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClientChatComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClientChatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
