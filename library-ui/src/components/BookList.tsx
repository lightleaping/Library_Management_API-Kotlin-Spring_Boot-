import React from "react";

type Book = {
    id: number;
    title: string;
    author: string;
};

export default function BookList({ books }: { books: Book[] }) {
    return (
        <div className="bg-white shadow rounded-lg p-4">
            <h2 className="text-lg font-semibold mb-4">📖 Book List</h2>
            <ul className="divide-y divide-gray-200">
                {books.map((book) => (
                    <li key={book.id} className="py-2">
                        <span className="font-medium">{book.title}</span> — {book.author}
                    </li>
                ))}
            </ul>
        </div>
    );
}