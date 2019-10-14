import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LaunchProcessComponent } from './launch-process.component';

describe('LaunchProcessComponent', () => {
  let component: LaunchProcessComponent;
  let fixture: ComponentFixture<LaunchProcessComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LaunchProcessComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LaunchProcessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
