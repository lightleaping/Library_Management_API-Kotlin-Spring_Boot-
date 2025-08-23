import api from "./client";
import type { Book } from "../types";

/**
 * 백엔드가 배열([Book])을 바로 주거나,
 * { content: Book[], ... } 같은 페이징 래퍼로 줄 수도 있으니
 * 두 케이스를 모두 처리.
 */
export async function fetchBooks(): Promise<Book[]> {
    const { data } = await api.get("/api/books");
    if (Array.isArray(data)) {
        return data as Book[];
    }
    if (data && Array.isArray(data.content)) {
        return data.content as Book[];
    }
    throw new Error("리스트 응답 형식이 올바르지 않습니다.");
}

export async function fetchBookById(id: number): Promise<Book> {
    const { data } = await api.get(`/api/books/${id}`);
    return data as Book;
}

export async function createBook(payload: Omit<Book, "id">): Promise<Book> {
    const { data } = await api.post("/api/books", payload);
    return data as Book;
}

export async function updateBook(id: number, payload: Partial<Book>): Promise<Book> {
    const { data } = await api.put(`/api/books/${id}`, payload);
    return data as Book;
}

export async function deleteBook(id: number): Promise<void> {
    await api.delete(`/api/books/${id}`);
}