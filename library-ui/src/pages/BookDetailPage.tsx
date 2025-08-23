import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { fetchBookById } from "../api/books";
import type { Book } from "../types";

export default function BookDetailPage() {
    const { id } = useParams<{ id: string }>();

    // 훅은 조건문 밖에서 선언
    const [book, setBook] = useState<Book | null>(null);
    const [error, setError] = useState<string>("");

    useEffect(() => {
        if (!id) {
            setError("잘못된 접근입니다 (id 없음).");
            return;
        }
        const num = Number(id);
        if (Number.isNaN(num)) {
            setError("잘못된 id 형식입니다.");
            return;
        }

        fetchBookById(num)
            .then(setBook)
            .catch((e) => {
                console.error(e);
                setError("서버 요청 실패");
            });
    }, [id]);

    if (error) return <div className="p-6 text-red-600">{error}</div>;
    if (!book) return <div className="p-6">불러오는 중...</div>;

    return (
        <div className="max-w-2xl mx-auto p-6 space-y-4">
            <Link to="/" className="text-sm text-blue-600 hover:underline">← 목록으로</Link>
            <h1 className="text-2xl font-bold">{book.title}</h1>
            <div className="text-gray-600">{book.author}</div>
            <div className="text-lg">{book.price.toLocaleString()}원</div>
        </div>
    );
}