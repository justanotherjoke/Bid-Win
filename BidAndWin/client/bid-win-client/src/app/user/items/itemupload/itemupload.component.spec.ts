import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemuploadComponent } from './itemupload.component';

describe('ItemuploadComponent', () => {
  let component: ItemuploadComponent;
  let fixture: ComponentFixture<ItemuploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ItemuploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ItemuploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
