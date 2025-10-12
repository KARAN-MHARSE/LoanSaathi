export class OtpModel{
	code: string;
    private email: string;

    constructor(code:string,email:string){
        this.code = code;
        this.email = email
    }
}