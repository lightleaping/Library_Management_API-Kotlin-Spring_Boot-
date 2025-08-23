import { useEffect, useState } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import { fetchBookById, deleteBook } from "../api/books";
import type { Book } from "../types";

export default function BookDetailPage() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [book, setBook] = useState<Book | null>(null);
    const [err, setErr] = useState("");

    const num = Number(id);
    useEffect(() => {
        if (!Number.isFinite(num)) {
            setErr("잘못된 접근입니다 (id 없음).");
            return;
        }
        fetchBookById(num)
            .then(setBook)
            .catch(() => setErr("상세 조회 실패"));
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [id]);

    const onDelete = async () => {
        if (!book) return;
        if (!window.confirm("정말 삭제할까요?")) return;
        try {
            await deleteBook(book.id);
            navigate("/");
        } catch {
            alert("삭제 실패");
        }
    };

    if (err) return <div className="p-6 text-red-600">{err}</div>;
    if (!book) return <div className="p-6">불러오는 중...</div>;

    return (
        <div className="p-6 space-y-4">
            <Link to="/" className="text-blue-700 underline">
                ← 목록
            </Link>
            <h1 className="text-2xl font-semibold">{book.title}</h1>
            <p className="text-gray-700">저자: {book.author}</p>
            <p className="text-gray-700">가격: {book.price.toLocaleString()}원</p>

            <div className="flex gap-2">
                <button
                    onClick={() => navigate(`/books/${book.id}/edit`)}
                    className="rounded bg-slate-600 px-3 py-2 text-white"
                >
                    수정
                </button>
                <button onClick={onDelete} className="rounded bg-red-600 px-3 py-2 text-white">
                    삭제
                </button>
            </div>
        </div>
    );
}