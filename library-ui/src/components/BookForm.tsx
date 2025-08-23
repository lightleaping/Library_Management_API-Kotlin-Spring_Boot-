import { useState } from "react";
import type { Book } from "../types";

type Props = {
    initial?: Partial<Book>;
    onSubmit: (data: Omit<Book, "id">) => Promise<void> | void;
    submitting?: boolean;
};

export default function BookForm({ initial, onSubmit, submitting }: Props) {
    const [title, setTitle] = useState(initial?.title ?? "");
    const [author, setAuthor] = useState(initial?.author ?? "");
    const [price, setPrice] = useState<string>(
        initial?.price !== undefined ? String(initial.price) : ""
    );
    const [err, setErr] = useState<string>("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setErr("");
        if (!title.trim() || !author.trim()) return setErr("제목/저자를 입력하세요.");
        const priceNum = Number(price);
        if (!Number.isFinite(priceNum) || priceNum < 0) return setErr("가격을 올바르게 입력하세요.");
        await onSubmit({ title: title.trim(), author: author.trim(), price: priceNum });
    };

    return (
        <form onSubmit={handleSubmit} className="max-w-md space-y-4">
            {err && <p className="text-red-600 text-sm">{err}</p>}
            <div>
                <label className="block text-sm font-medium">제목</label>
                <input
                    className="mt-1 w-full rounded border px-3 py-2"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    placeholder="예) Clean Code"
                />
            </div>
            <div>
                <label className="block text-sm font-medium">저자</label>
                <input
                    className="mt-1 w-full rounded border px-3 py-2"
                    value={author}
                    onChange={(e) => setAuthor(e.target.value)}
                    placeholder="예) Robert C. Martin"
                />
            </div>
            <div>
                <label className="block text-sm font-medium">가격</label>
                <input
                    className="mt-1 w-full rounded border px-3 py-2"
                    value={price}
                    onChange={(e) => setPrice(e.target.value)}
                    inputMode="numeric"
                    placeholder="예) 27000"
                />
            </div>
            <button
                disabled={!!submitting}
                className="rounded bg-blue-600 px-4 py-2 text-white disabled:opacity-60"
            >
                {submitting ? "저장중..." : "저장"}
            </button>
        </form>
    );
}