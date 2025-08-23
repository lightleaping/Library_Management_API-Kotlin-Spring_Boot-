import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { fetchBooks, deleteBook } from "../api/books";
import type { Book } from "../types";

export default function BookListPage() {
    const [items, setItems] = useState<Book[]>([]);
    const [loading, setLoading] = useState(false);
    const [err, setErr] = useState<string>("");
    const navigate = useNavigate();

    useEffect(() => {
        (async () => {
            try {
                setLoading(true);
                const data = await fetchBooks();
                setItems(data);
            } catch (e) {
                console.error(e);
                setErr("목록 조회 실패");
            } finally {
                setLoading(false);
            }
        })();
    }, []);

    const onDelete = async (id: number) => {
        if (!window.confirm("정말 삭제할까요?")) return;
        // 낙관적 업데이트
        const prev = items;
        setItems((curr) => curr.filter((b) => b.id !== id));
        try {
            await deleteBook(id);
        } catch (e) {
            alert("삭제 실패");
            setItems(prev);
        }
    };

    return (
        <div className="p-6 space-y-4">
            <div className="flex items-center justify-between">
                <h1 className="text-2xl font-semibold">도서 목록</h1>
                <button
                    className="rounded bg-green-600 px-3 py-2 text-white"
                    onClick={() => navigate("/books/new")}
                >
                    + 새 도서
                </button>
            </div>
            {loading && <p>불러오는 중...</p>}
            {err && <p className="text-red-600">{err}</p>}
            {!loading && !err && (
                <ul className="divide-y rounded border">
                    {items.map((b) => (
                        <li key={b.id} className="flex items-center justify-between p-3">
                            <div>
                                <Link to={`/books/${b.id}`} className="text-blue-700 underline">
                                    {b.title}
                                </Link>
                                <span className="ml-2 text-sm text-gray-600">/ {b.author}</span>
                            </div>
                            <div className="flex items-center gap-2">
                                <span className="text-gray-700">{b.price.toLocaleString()}원</span>
                                <button
                                    onClick={() => navigate(`/books/${b.id}/edit`)}
                                    className="rounded bg-slate-600 px-2 py-1 text-white"
                                >
                                    수정
                                </button>
                                <button
                                    onClick={() => onDelete(b.id)}
                                    className="rounded bg-red-600 px-2 py-1 text-white"
                                >
                                    삭제
                                </button>
                            </div>
                        </li>
                    ))}
                    {items.length === 0 && <li className="p-4 text-gray-500">데이터가 없습니다.</li>}
                </ul>
            )}
        </div>
    );
}