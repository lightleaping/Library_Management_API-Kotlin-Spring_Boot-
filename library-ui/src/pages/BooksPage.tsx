import React, { useEffect, useState } from "react";
import BookList from "../components/BookList";
import BookForm from "../components/BookForm";

type Book = {
    id: number;
    title: string;
    author: string;
};

export default function BooksPage() {
    const [books, setBooks] = useState<Book[]>([]);

    useEffect(() => {
        fetch("/api/books")
            .then((res) => res.json())
            .then(setBooks)
            .catch((err) => console.error(err));
    }, []);

    return (
        <div className="space-y-6">
            <BookForm onBookAdded={(book) => setBooks([...books, book])} />
            <BookList books={books} />
        </div>
    );
}