import { DocumentType } from "./DocumentType.model";

export class Document {
    id: number | null = null;
    type: DocumentType | null = null;
    name: string | null = null;
    url: string | null = null;
    uploadedAt: Date | null = null;
}