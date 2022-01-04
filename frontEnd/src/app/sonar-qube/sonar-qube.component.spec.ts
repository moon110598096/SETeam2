import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SonarQubeComponent } from './sonar-qube.component';

describe('SonarQubeComponent', () => {
  let component: SonarQubeComponent;
  let fixture: ComponentFixture<SonarQubeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SonarQubeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SonarQubeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
