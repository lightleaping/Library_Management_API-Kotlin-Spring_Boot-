import { useEffect, useMemo, useState } from "react";
import { fetchBooks, createBook, updateBook, deleteBook } from "../api/books";
import type { Book } from "../types";

type FormState = {
    id?: number | null;
    title: string;
    author: string;
    price: number | "";
    description?: string;
};

const emptyForm: FormState = {
    id: null,
    title: "",
    author: "",
    price: "",
    description: "",
};

export default function BooksPage() {
    const [items, setItems] = useState<Book[]>([]);
    const [loading, setLoading] = useState(false);
    const [form, setForm] = useState<FormState>(emptyForm);
    const [error, setError] = useState<string | null>(null);
    const [keyword, setKeyword] = useState("");

    // 목록 불러오기
    async function load() {
        try {
            setLoading(true);
            setError(null);
            const data = await fetchBooks();
            setItems(data);
        } catch (e) {
            console.error(e);
            setError("도서 목록을 불러오지 못했습니다.");
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        load();
    }, []);

    // 검색 필터
    const filtered = useMemo(() => {
        if (!keyword.trim()) return items;
        const q = keyword.trim().toLowerCase();
        return items.filter(
            (b) =>
                b.title.toLowerCase().includes(q) ||
                b.author.toLowerCase().includes(q) ||
                String(b.id ?? "").includes(q)
        );
    }, [items, keyword]);

    // 입력 변경
    function onChange<K extends keyof FormState>(key: K, val: FormState[K]) {
        setForm((f) => ({ ...f, [key]: val }));
    }

    // 수정모드 진입
    function onEditClick(b: Book) {
        setForm({
            id: b.id ?? null,
            title: b.title,
            author: b.author,
            price: b.price,
            description: (b as any).description ?? "",
        });
        window.scrollTo({ top: 0, behavior: "smooth" });
    }

    // 삭제
    async function onDeleteClick(id?: number) {
        if (!id) return;
        if (!confirm("정말 삭제할까요?")) return;
        try {
            setLoading(true);
            await deleteBook(id);
            await load();
        } catch (e) {
            console.error(e);
            alert("삭제 실패");
        } finally {
            setLoading(false);
        }
    }

    // 저장(신규/수정)
    async function onSubmit(e: React.FormEvent) {
        e.preventDefault();
        if (!form.title.trim() || !form.author.trim() || form.price === "") {
            alert("제목/저자/가격을 입력해 주세요.");
            return;
        }
        try {
            setLoading(true);
            setError(null);

            const payload: Omit<Book, "id"> & Partial<Pick<Book, "id">> = {
                id: form.id ?? undefined,
                title: form.title.trim(),
                author: form.author.trim(),
                price:
                    typeof form.price === "string" ? Number(form.price) : form.price ?? 0,
                // 백엔드에 description 필드가 없으면 그냥 무시됩니다.
                ...(form.description ? { description: form.description } : {}),
            };

            if (form.id) {
                await updateBook(form.id, payload as Book);
            } else {
                await createBook(payload as Book);
            }

            setForm(emptyForm);
            await load();
        } catch (e) {
            console.error(e);
            setError("저장 중 오류가 발생했습니다.");
        } finally {
            setLoading(false);
        }
    }

    // 폼 초기화
    function resetForm() {
        setForm(emptyForm);
    }

    return (
        <div className="max-w-5xl mx-auto p-6">
            <h1 className="text-2xl font-bold mb-4">도서 관리</h1>

            {/* 오류/로딩 */}
            {error && (
                <div className="mb-4 rounded-md border border-red-300 bg-red-50 px-4 py-3 text-sm text-red-700">
                    {error}
                </div>
            )}
            {loading && (
                <div className="mb-4 rounded-md border bg-gray-50 px-4 py-3 text-sm text-gray-700">
                    처리 중…
                </div>
            )}

            {/* 입력 폼 */}
            <form
                onSubmit={onSubmit}
                className="mb-8 grid grid-cols-1 gap-3 rounded-xl border p-4 md:grid-cols-2"
            >
                <div className="md:col-span-2 flex items-center justify-between">
                    <h2 className="text-lg font-semibold">
                        {form.id ? `도서 수정 #${form.id}` : "신규 도서 추가"}
                    </h2>
                    {form.id && (
                        <button
                            type="button"
                            onClick={resetForm}
                            className="rounded-lg border px-3 py-1 text-sm"
                        >
                            새로 입력
                        </button>
                    )}
                </div>

                <label className="flex flex-col gap-1">
                    <span className="text-sm text-gray-600">제목</span>
                    <input
                        className="rounded-lg border px-3 py-2"
                        value={form.title}
                        onChange={(e) => onChange("title", e.target.value)}
                        placeholder="예) Clean Code"
                    />
                </label>

                <label className="flex flex-col gap-1">
                    <span className="text-sm text-gray-600">저자</span>
                    <input
                        className="rounded-lg border px-3 py-2"
                        value={form.author}
                        onChange={(e) => onChange("author", e.target.value)}
                        placeholder="예) Robert C. Martin"
                    />
                </label>

                <label className="flex flex-col gap-1">
                    <span className="text-sm text-gray-600">가격(원)</span>
                    <input
                        type="number"
                        className="rounded-lg border px-3 py-2"
                        value={form.price}
                        onChange={(e) => onChange("price", e.target.valueAsNumber || "")}
                        placeholder="예) 27000"
                        min={0}
                    />
                </label>

                <label className="flex flex-col gap-1 md:col-span-2">
                    <span className="text-sm text-gray-600">설명(선택)</span>
                    <textarea
                        className="min-h-[80px] rounded-lg border px-3 py-2"
                        value={form.description ?? ""}
                        onChange={(e) => onChange("description", e.target.value)}
                        placeholder="간단한 설명"
                    />
                </label>

                <div className="md:col-span-2 flex gap-2">
                    <button
                        type="submit"
                        className="rounded-lg bg-blue-600 px-4 py-2 text-white hover:bg-blue-700"
                        disabled={loading}
                    >
                        {form.id ? "수정 저장" : "추가"}
                    </button>
                    <button
                        type="button"
                        onClick={resetForm}
                        className="rounded-lg border px-4 py-2"
                        disabled={loading}
                    >
                        초기화
                    </button>
                </div>
            </form>

            {/* 검색 */}
            <div className="mb-3 flex items-center justify-between gap-3">
                <input
                    className="w-full rounded-lg border px-3 py-2 md:max-w-xs"
                    placeholder="검색(제목/저자/ID)"
                    value={keyword}
                    onChange={(e) => setKeyword(e.target.value)}
                />
                <button
                    className="rounded-lg border px-3 py-2 text-sm"
                    onClick={() => setKeyword("")}
                >
                    지우기
                </button>
            </div>

            {/* 목록 */}
            <div className="overflow-x-auto">
                <table className="min-w-full border rounded-xl overflow-hidden">
                    <thead className="bg-gray-50">
                    <tr>
                        <th className="px-3 py-2 text-left text-sm text-gray-500">ID</th>
                        <th className="px-3 py-2 text-left text-sm text-gray-500">
                            제목
                        </th>
                        <th className="px-3 py-2 text-left text-sm text-gray-500">
                            저자
                        </th>
                        <th className="px-3 py-2 text-left text-sm text-gray-500">
                            가격
                        </th>
                        <th className="px-3 py-2 text-left text-sm text-gray-500">
                            액션
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {filtered.length === 0 && (
                        <tr>
                            <td
                                colSpan={5}
                                className="px-3 py-6 text-center text-gray-500 text-sm"
                            >
                                데이터가 없습니다.
                            </td>
                        </tr>
                    )}
                    {filtered.map((b) => (
                        <tr key={b.id} className="border-t">
                            <td className="px-3 py-2 text-sm">{b.id}</td>
                            <td className="px-3 py-2 text-sm">{b.title}</td>
                            <td className="px-3 py-2 text-sm">{b.author}</td>
                            <td className="px-3 py-2 text-sm">
                                {new Intl.NumberFormat().format(b.price)}원
                            </td>
                            <td className="px-3 py-2">
                                <div className="flex gap-2">
                                    <button
                                        className="rounded-md border px-3 py-1 text-sm hover:bg-gray-50"
                                        onClick={() => onEditClick(b)}
                                        disabled={loading}
                                    >
                                        수정
                                    </button>
                                    <button
                                        className="rounded-md border px-3 py-1 text-sm text-red-600 hover:bg-red-50"
                                        onClick={() => onDeleteClick(b.id)}
                                        disabled={loading}
                                    >
                                        삭제
                                    </button>
                                </div>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}