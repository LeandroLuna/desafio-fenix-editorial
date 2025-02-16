import { TestBed } from '@angular/core/testing';
import { HttpClientService } from './http-client.service';
import { provideHttpClient } from '@angular/common/http';

describe('HttpClientService', () => {
  let service: HttpClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        HttpClientService,
        provideHttpClient()
      ]
    });
    service = TestBed.inject(HttpClientService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
