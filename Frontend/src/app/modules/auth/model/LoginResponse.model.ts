export interface LoginResponse {
    username: string,
    role: string;
    token?: string; // if you return JWT
}