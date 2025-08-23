import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { fetchBooks } from "../api/books";
import type { Book } from "../types";

export default function BookListPage() {
    const [items, setItems] = useState<Book[]>([]);
    const [error, setError] = useState<string>("");
    const [loading, setLoading] = useState<boolean>(false);

    useEffect(() => {
        (async () => {
            try {
                setLoading(true);
                const data = await fetchBooks();
                setItems(data);
            } catch (e) {
                console.error(e);
                setError(e instanceof Error ? e.message : "알 수 없는 오류");
            } finally {
                setLoading(false);
            }
        })();
    }, []);

    if (loading) return <div className="p-6">불러오는 중...</div>;
    if (error)   return <div className="p-6 text-red-600">{error}</div>;

    return (
        <div className="max-w-3xl mx-auto p-6 space-y-4">
            <h1 className="text-2xl font-bold">도서 목록</h1>
            {items.length === 0 ? (
                <div className="text-gray-500">데이터가 없습니다.</div>
            ) : (
                <ul className="divide-y divide-gray-200 rounded-2xl shadow">
                    {items.map((b) => (
                        <li key={b.id} className="p-4 hover:bg-gray-50 flex items-center justify-between">
                            <div>
                                <div className="font-medium">{b.title}</div>
                                <div className="text-sm text-gray-500">{b.author}</div>
                            </div>
                            <div className="flex items-center gap-3">
                                <div className="text-sm text-gray-600">{b.price.toLocaleString()}원</div>
                                <Link
                                    to={`/books/${b.id}`}
                                    className="px-3 py-1 rounded-xl border hover:bg-gray-100"
                                >
                                    상세
                                </Link>
                            </div>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}