import { useEffect, useState } from "react";
import { useNavigate, useParams, Link } from "react-router-dom";
import BookForm from "../components/BookForm";
import { fetchBookById, updateBook } from "../api/books";
import type { Book } from "../types";

export default function BookEditPage() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [initial, setInitial] = useState<Partial<Book>>({});
    const [submitting, setSubmitting] = useState(false);
    const [err, setErr] = useState("");

    const num = Number(id);
    useEffect(() => {
        if (!Number.isFinite(num)) {
            setErr("잘못된 접근입니다 (id 없음).");
            return;
        }
        fetchBookById(num)
            .then(setInitial)
            .catch(() => setErr("상세 조회 실패"));
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [id]);

    const handleSubmit = async (data: Omit<Book, "id">) => {
        try {
            setSubmitting(true);
            const updated = await updateBook(num, data);
            navigate(`/books/${updated.id}`);
        } finally {
            setSubmitting(false);
        }
    };

    if (err) return <div className="p-6 text-red-600">{err}</div>;
    if (!initial?.title) return <div className="p-6">불러오는 중...</div>;

    return (
        <div className="p-6 space-y-4">
            <Link to="/" className="text-blue-700 underline">← 목록</Link>
            <h1 className="text-2xl font-semibold">도서 수정</h1>
            <BookForm initial={initial} onSubmit={handleSubmit} submitting={submitting} />
        </div>
    );
}