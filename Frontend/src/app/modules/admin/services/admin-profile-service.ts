import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

interface UserProfile {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  dateOfBirth: string;
  roleName: string;
  profileImageUrl?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AdminProfileService {
  private readonly apiUrl = 'http://localhost:5000/api/v1/users/profile';

  constructor(private http: HttpClient) {}

  getProfile(email: string): Observable<UserProfile> {
    const params = new HttpParams().set('email', email);
    return this.http.get<UserProfile>(this.apiUrl, { params });
  }
}
