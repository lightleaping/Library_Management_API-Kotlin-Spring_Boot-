import { useEffect, useState } from 'react'
import { api } from './api'

type Book = { id: number; title: string; author: string; price: number }

export default function App() {
    const [books, setBooks] = useState<Book[]>([])
    const [error, setError] = useState<string | null>(null)

    useEffect(() => {
        api.get('/books')
            .then(res => setBooks(res.data.content ?? res.data))
            .catch(e => setError(e.message))
    }, [])

    return (
        <div style={{ padding: 24 }}>
            <h1>Hero Library</h1>
            {error && <p style={{color:'crimson'}}>에러: {error}</p>}
            <ul>
                {books.map(b => (
                    <li key={b.id}>
                        {b.title} / {b.author} / {b.price.toLocaleString()}원
                    </li>
                ))}
            </ul>
        </div>
    )
}