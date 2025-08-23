import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import BookForm from "../components/BookForm";
import { createBook } from "../api/books";
import type { Book } from "../types";

export default function BookNewPage() {
    const [submitting, setSubmitting] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = async (data: Omit<Book, "id">) => {
        try {
            setSubmitting(true);
            const created = await createBook(data);
            navigate(`/books/${created.id}`);
        } finally {
            setSubmitting(false);
        }
    };

    return (
        <div className="p-6 space-y-4">
            <Link to="/" className="text-blue-700 underline">← 목록</Link>
            <h1 className="text-2xl font-semibold">새 도서 등록</h1>
            <BookForm onSubmit={handleSubmit} submitting={submitting} />
        </div>
    );
}