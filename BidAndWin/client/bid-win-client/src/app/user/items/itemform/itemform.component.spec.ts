import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemformComponent } from './itemform.component';

describe('ItemformComponent', () => {
  let component: ItemformComponent;
  let fixture: ComponentFixture<ItemformComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ItemformComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ItemformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
