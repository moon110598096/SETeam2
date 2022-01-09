import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SonarQubeHistoryComponent } from './sonar-qube-history.component';

describe('SonarQubeHistoryComponent', () => {
  let component: SonarQubeHistoryComponent;
  let fixture: ComponentFixture<SonarQubeHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SonarQubeHistoryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SonarQubeHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
