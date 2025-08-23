// src/api/books.ts
import api from "./client";
import type { Book } from "../types";

// 배열 또는 Page 래퍼 둘 다 허용
function unwrapList(data: unknown): Book[] {
    if (Array.isArray(data)) return data as Book[];
    if (data && typeof data === "object" && Array.isArray((data as any).content)) {
        return (data as any).content as Book[];
    }
    throw new Error("리스트 응답 형식이 올바르지 않습니다.");
}

export async function fetchBooks(): Promise<Book[]> {
    const { data } = await api.get("/books");
    return unwrapList(data);
}

export async function fetchBookById(id: number): Promise<Book> {
    const { data } = await api.get(`/books/${id}`);
    return data as Book;
}

export async function createBook(payload: Omit<Book, "id">): Promise<Book> {
    const { data } = await api.post("/books", payload);
    return data as Book;
}

export async function updateBook(id: number, payload: Partial<Book>): Promise<Book> {
    const { data } = await api.put(`/books/${id}`, payload);
    return data as Book;
}

export async function deleteBook(id: number): Promise<void> {
    await api.delete(`/books/${id}`);
}