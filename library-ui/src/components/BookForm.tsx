import React, { useState } from "react";

type Book = {
    id: number;
    title: string;
    author: string;
};

export default function BookForm({ onBookAdded }: { onBookAdded: (book: Book) => void }) {
    const [title, setTitle] = useState("");
    const [author, setAuthor] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const res = await fetch("/api/books", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ title, author }),
        });
        if (res.ok) {
            const newBook: Book = await res.json();
            onBookAdded(newBook);
            setTitle("");
            setAuthor("");
        }
    };

    return (
        <form
            onSubmit={handleSubmit}
            className="bg-white shadow rounded-lg p-4 space-y-4"
        >
            <h2 className="text-lg font-semibold">➕ Add New Book</h2>
            <input
                type="text"
                placeholder="Title"
                className="border p-2 w-full rounded"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                required
            />
            <input
                type="text"
                placeholder="Author"
                className="border p-2 w-full rounded"
                value={author}
                onChange={(e) => setAuthor(e.target.value)}
                required
            />
            <button
                type="submit"
                className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
            >
                Add Book
            </button>
        </form>
    );
}