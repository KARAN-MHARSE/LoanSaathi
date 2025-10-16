import { HttpClient } from '@angular/common/http';
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
  providedIn: 'root',
})
export class AdminProfileService {
  private readonly apiUrl = 'http://localhost:5000/api/v1/users/profile';

  constructor(private http: HttpClient) {}

  getProfile(): Observable<UserProfile> {
    return this.http.get<UserProfile>(this.apiUrl);
  }

  getProfilePicture(): Observable<{ profileUrl: string }> {
    return this.http.get<{ profileUrl: string }>(`${this.apiUrl}/pic`);
  }

  updateProfilePic(formData: FormData) {
    return this.http.put<{ profileUrl: string }>(
      'http://localhost:5000/api/v1/users/profile/image',
      formData
    );
  }

  updateUserProfile(data: {
    firstName: string;
    lastName: string;
    phoneNumber: string;
    dateOfBirth: string;
  }): Observable<any> {
    return this.http.put('http://localhost:5000/api/v1/users/profile', data);
  }
}
